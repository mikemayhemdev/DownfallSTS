package slimebound.cards;


import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;


public class Split extends AbstractSlimeboundCard implements OctopusCard {
    public static final String ID = "Slimebound:Split";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/split.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    public String[] NAMES = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").NAMES;
    public String[] TEXT = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").TEXT;

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<OctoChoiceCard> cardList = new ArrayList<>();


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Split() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 0;
        this.exhaust = true;


            cardList.add(new OctoChoiceCard("Slimebound:SplotBruiser",
                    CardCrawlGame.languagePack.getOrbString("Slimebound:AttackSlime").NAME,
                    SlimeboundMod.getResourcePath("cards/splitBruiser.png"),
                    TEXT[20] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:bruiser_slime").replaceAll("#b","").replaceAll("#y","")));
            cardList.add(new OctoChoiceCard("Slimebound:SplotGuerilla",
                    CardCrawlGame.languagePack.getOrbString("Slimebound:PoisonSlime").NAME,
                    SlimeboundMod.getResourcePath(IMG_PATH),
                    TEXT[21] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:guerilla_slime").replaceAll("#b","").replaceAll("#y","")));
            cardList.add(new OctoChoiceCard("Slimebound:SplotMire",
                    CardCrawlGame.languagePack.getOrbString("Slimebound:SlimingSlime").NAME,
                    SlimeboundMod.getResourcePath("cards/splitMire.png"),
                    TEXT[22] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:mire_slime").replaceAll("#b","").replaceAll("#y","")));
            cardList.add(new OctoChoiceCard("Slimebound:SplotLeeching",
                    CardCrawlGame.languagePack.getOrbString("Slimebound:ShieldSlime").NAME,
                    SlimeboundMod.getResourcePath("cards/splitLeeching.png"),
                    TEXT[23] + " NL " + BaseMod.getKeywordDescription("slimeboundmod:leeching_slime").replaceAll("#b","").replaceAll("#y","")));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        choice(m);


        //Forever shall this code remain commented here.  A legacy to the origins of how bad Mayhem was at coding when this all began.
        /*        for (int i = 0; i < magicNumber; i++) {
            ArrayList<Integer> orbs = new ArrayList<>();
            orbs.add(1);
            orbs.add(2);
            orbs.add(3);
            orbs.add(4);
            Integer o = orbs.get(AbstractDungeon.cardRng.random(orbs.size() - 1));

                    switch (o) {
                case 1:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new AttackSlime(), false, true));
                    break;
                case 2:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, true));
                    break;
                case 3:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new SlimingSlime(), false, true));
                    break;
                case 4:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, true));
                    break;
         */
    }

    public void choice(AbstractMonster m) {
        addToBot(new OctoChoiceAction(m,this));
        if (upgraded) addToBot(new OctoChoiceAction(m,this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> realList = new ArrayList<>();

        if (baseMagicNumber != 1)
            realList.add(cardList.get(0));
        if (baseMagicNumber != 2)
            realList.add(cardList.get(1));
        if (baseMagicNumber != 3)
            realList.add(cardList.get(2));
        if (baseMagicNumber != 4)
            realList.add(cardList.get(3));

        return realList;

    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "Slimebound:SplotBruiser": {
                addToBot(new SlimeSpawnAction(new AttackSlime(), false, true));
                updateMagic(1);
                break;
            }
            case "Slimebound:SplotGuerilla": {
                addToBot(new SlimeSpawnAction(new PoisonSlime(), false, true));
                updateMagic(2);
                break;
            }
            case "Slimebound:SplotMire": {
                addToBot(new SlimeSpawnAction(new SlimingSlime(), false, true));
                updateMagic(3);
                break;
            }
            case "Slimebound:SplotLeeching": {
                addToBot(new SlimeSpawnAction(new ShieldSlime(), false, true));
                updateMagic(4);
                break;
            }

        }
    }

    private void updateMagic(int index) {
        if (upgraded) {
            if (this.baseMagicNumber == 0) {
                this.baseMagicNumber = index;
            } else {
                this.baseMagicNumber = 0;
            }
        } else {
            this.baseMagicNumber = 0;
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }



    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (cardList.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = cardList.get(previewIndex);
                }
                if (previewIndex == cardList.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        cardsToPreview = null;
    }
}

