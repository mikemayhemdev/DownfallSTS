package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHexaghost.powers.FutureUpgradePower;

import java.util.ArrayList;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class Barbells extends CustomRelic {

    public static final String ID = ChampMod.makeID("Barbells");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Barbell.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Barbell.png"));


    private boolean used;
    public Barbells() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public void onEnterRestRoom() {
        if (!used) {
            flash();
            this.pulse = true;
            used = true;
            ArrayList<AbstractCard> possibleCards = new ArrayList<>();// 38
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {// 40
                    possibleCards.add(c);// 41
                }
            }

            if (possibleCards.size() >= 10) {// 45
                AbstractCard card = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));// 46
                card.upgrade();// 47
                AbstractDungeon.player.bottledCardUpgradeCheck(card);// 48
                card.upgrade();
                float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), x, y));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
            }
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (!(room instanceof RestRoom)){
            used = false;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
