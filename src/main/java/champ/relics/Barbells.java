package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
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

import java.util.ArrayList;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class Barbells extends CustomRelic implements OnRemoveCardFromMasterDeckRelic {
    public static final String ID = ChampMod.makeID("Barbells");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Barbell.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Barbell.png"));

    public Barbells() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    //# of required cards to perform an upgrade
    private static final int AMOUNT = 10;


    @Override
    public void onEquip() {
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();// 38
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {// 40
                possibleCards.add(c);// 41
            }
        }
        this.counter = possibleCards.size();
    }


    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard var1) {
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();// 38
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {// 40
                possibleCards.add(c);// 41
            }
        }
        this.counter = possibleCards.size();
    }

    public void onObtainCard(AbstractCard c) {
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();// 38
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.canUpgrade()) {// 40
                possibleCards.add(card);// 41
            }
        }
        this.counter = possibleCards.size();
    }


    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof RestRoom) {
            flash();

            ArrayList<AbstractCard> possibleCards = new ArrayList<>();// 38
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {// 40
                    possibleCards.add(c);// 41
                }
            }
            this.counter = possibleCards.size();

            if (possibleCards.size() >= AMOUNT) {// 45
                this.counter = possibleCards.size() - 1;
                AbstractCard card = possibleCards.get(AbstractDungeon.relicRng.random(0, possibleCards.size() - 1));// 46
                card.upgrade();// 47
                AbstractDungeon.player.bottledCardUpgradeCheck(card);// 48
                float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), x, y));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
            }
        }
    }


    public boolean canSpawn() {
        return Settings.isEndless || (AbstractDungeon.floorNum <= 40); // cannot appear past early act 3
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
