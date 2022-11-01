package hermit.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.cards.MementoCard;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class Memento extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("Momento");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("memento.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("memento.png"));

    private final int TURNS = 0;

    public Memento() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
        tips.add(new CardPowerTip(new MementoCard()));
    }
    // Gain 1 Strength on on equip.

    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToTop(new MakeTempCardInHandAction(new MementoCard(), 1, false));
    }


    /*
    public void onObtainCard(AbstractCard card) {

        AbstractCard c;
        AbstractCard upgradeCard;
        ArrayList<AbstractCard> upgradableCards = new ArrayList();

        if (card.color == AbstractCard.CardColor.CURSE) {
            Iterator var9 = AbstractDungeon.player.masterDeck.group.iterator();

            while(var9.hasNext()) {
                c = (AbstractCard)var9.next();
                if (c.canUpgrade()) {
                    upgradableCards.add(c);
                }
            }

            Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
            if (!upgradableCards.isEmpty()) {
                upgradeCard = (AbstractCard)upgradableCards.get(0);
                upgradeCard.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradeCard);
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(upgradeCard.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }

    }
    */

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
