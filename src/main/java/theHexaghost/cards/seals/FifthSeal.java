package theHexaghost.cards.seals;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import guardian.cards.*;
import theHexaghost.powers.SealPostCombatPower;

import java.util.ArrayList;

@NoCompendium
public class FifthSeal extends AbstractSealCard {
    public final static String ID = makeID("FifthSeal");

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();
    public static final int MAGIC = 2;

    public FifthSeal() {
        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
//
//        cardsList.add(new FirstSeal());
//        cardsList.add(new SecondSeal());
//        cardsList.add(new ThirdSeal());
//        cardsList.add(new FourthSeal());
//        cardsList.add(new SixthSeal());
        this.tags.add(AbstractCard.CardTags.HEALING);
        HexaMod.loadJokeCardImage(this, "FifthSeal.png");
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
//        applyToSelf(new SealPostCombatPower(1));
        AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }

//    @Override
//    public void update() {
//        super.update();
//        if (hb.hovered) {
//            if (rotationTimer <= 0F) {
//                rotationTimer = 2F;
//                if (cardsList.size() == 0) {
//                    cardsToPreview = CardLibrary.cards.get("Madness");
//                } else {
//                    cardsToPreview = cardsList.get(previewIndex);
//                }
//                if (previewIndex == cardsList.size() - 1) {
//                    previewIndex = 0;
//                } else {
//                    previewIndex++;
//                }
//            } else {
//                rotationTimer -= Gdx.graphics.getDeltaTime();
//            }
//        }
//    }
//
//    @Override
//    public void unhover() {
//        super.unhover();
//        cardsToPreview = null;
//    }
}