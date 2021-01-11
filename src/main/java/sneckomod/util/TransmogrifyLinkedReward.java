package sneckomod.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TransmogrifyLinkedReward extends RewardItem {
    public List<RewardItem> relicLinks = new ArrayList<>();

    public TransmogrifyLinkedReward(RewardItem original) {
        type = original.type;
        outlineImg = original.outlineImg;
        img = original.img;
        goldAmt = original.goldAmt;
        bonusGold = original.bonusGold;
        text = original.text;
        //relicLink = original.relicLink; // TODO?
        if (original.relicLink != null) {
            //relicLinks.add(original.relicLink);
        }
        relic = original.relic;
        potion = original.potion;
        cards = original.cards;
        //effects
        //isBoss
        hb = original.hb;
        y = original.y;
        flashTimer = original.flashTimer;
        isDone = original.isDone;
        ignoreReward = original.ignoreReward;
        redText = original.redText;
    }

    public TransmogrifyLinkedReward(TransmogrifyLinkedReward setRelicLink, AbstractRelic relic) {
        super(relic);

        addRelicLink(setRelicLink);
    }

    public void addRelicLink(TransmogrifyLinkedReward setRelicLink) {
        if (!relicLinks.contains(setRelicLink)) {
            relicLinks.add(setRelicLink);
        }
        if (!setRelicLink.relicLinks.contains(this)) {
            setRelicLink.relicLinks.add(this);
        }
    }

    private boolean isFirst() {
        //if (AbstractDungeon.getCurrRoom().rewards.indexOf(this) > AbstractDungeon.getCurrRoom().rewards.indexOf(relicLink)) {
        int thisIndexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(this);
        for (RewardItem link : relicLinks) {
            if (AbstractDungeon.getCurrRoom().rewards.indexOf(link) < thisIndexOf) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean claimReward() {
        boolean ret;
        if (type == RewardType.SAPPHIRE_KEY) {
            if (!ignoreReward) {
                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
            }
            ret = true;
        } else {
            ret = super.claimReward();
        }
        if (ret) {
            for (RewardItem link : relicLinks) {
                link.isDone = true;
                link.ignoreReward = true;
            }
        }
        return ret;
    }

    @Override
    public void update() {
        super.update();

        if (isFirst()) {
            redText = false;
            for (RewardItem link : relicLinks) {
                link.redText = false;
            }
        }

        if (hb.hovered) {
            for (RewardItem link : relicLinks) {
                link.redText = hb.hovered;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if (!relicLinks.isEmpty() && type != RewardType.SAPPHIRE_KEY) {
            if (hb.hovered) {
                // Make TipHelper think we haven't tried to render a tip this frame
                try {
                    Field f = TipHelper.class.getDeclaredField("renderedTipThisFrame");
                    f.setAccessible(true);
                    f.setBoolean(null, false);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

                ArrayList<PowerTip> tips = new ArrayList<>();
                tips.add(new PowerTip(relic.name, relic.description));
                for (RewardItem link : relicLinks) {
                    if (link.type == RewardType.SAPPHIRE_KEY) {
                        tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(TEXT[6] + TEXT[9], "y")));
                    } else if (link.relic != null) {
                        tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(link.relic.name, "y") + TEXT[9]));
                    }
                }
                TipHelper.queuePowerTips(360.0f * Settings.scale, InputHelper.mY + 50.0f * Settings.scale, tips);
            }

            if (!isFirst()) {
                renderRelicLink(sb);
            }
        } else if (!relicLinks.isEmpty() && type == RewardType.SAPPHIRE_KEY) {
            if (hb.hovered) {
                ArrayList<PowerTip> tips = new ArrayList<>();
                for (RewardItem link : relicLinks) {
                    tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(link.relic.name + TEXT[9], "y")));
                }
                TipHelper.queuePowerTips(360.0f * Settings.scale, InputHelper.mY + 50.0f * Settings.scale, tips);
            }
        }

        hb.render(sb);
    }

    @SpireOverride
    protected void renderRelicLink(SpriteBatch sb) {
        SpireSuper.call(sb);
    }
}