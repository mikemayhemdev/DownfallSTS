package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.OctoChoiceCard;
import expansioncontent.actions.OctoChoiceAction;
import expansioncontent.expansionContentMod;
import guardian.patches.GuardianEnum;
import slimebound.patches.SlimeboundEnum;
import theHexaghost.TheHexaghost;

import java.util.ArrayList;

public class QuickStudy extends AbstractExpansionCard {

    public final static String ID = makeID("QuickStudy");
    public String[] NAMES = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").NAMES;
    public String[] TEXT = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").TEXT;

    //stupid intellij stuff SKILL, SELF, RARE

    public QuickStudy() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        if (AbstractDungeon.player.chosenClass != GuardianEnum.GUARDIAN)
            cardList.add(new OctoChoiceCard(expansionContentMod.makeID("0"), NAMES[3], expansionContentMod.makeCardPath("QuickGuardian.png"), TEXT[3], new DefensiveMode(), new ChargeUp(), new GuardianWhirl()));
        if (AbstractDungeon.player.chosenClass != TheHexaghost.Enums.THE_SPIRIT)
            cardList.add(new OctoChoiceCard(expansionContentMod.makeID("1"), NAMES[4], expansionContentMod.makeCardPath("QuickHexa.png"), TEXT[4], new Hexaburn(), new SuperHexaguard(), new Sear()));
        if (AbstractDungeon.player.chosenClass != SlimeboundEnum.SLIMEBOUND)
            cardList.add(new OctoChoiceCard(expansionContentMod.makeID("2"), NAMES[5], expansionContentMod.makeCardPath("QuickSlime.png"), TEXT[5], new PrepareCrush(), new SlimeTackle(), new GoopSpray()));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("3"), NAMES[6], expansionContentMod.makeCardPath("QuickAutomaton.png"), TEXT[6], new BronzeBeam(), new HyperBeam(), new Flail()));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("4"), NAMES[7], expansionContentMod.makeCardPath("QuickChamp.png"), TEXT[7], new FaceSlap(), new LastStand(), new DefensiveStance()));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("5"), NAMES[8], expansionContentMod.makeCardPath("QuickCollector.png"), TEXT[8], new Collect(), new Torchfire(), new YouAreMine()));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("6"), NAMES[9], expansionContentMod.makeCardPath("QuickTimeEater.png"), TEXT[9], new ManipulateTime(), new TimeRipple(), new Chronoboost()));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("7"), NAMES[10], expansionContentMod.makeCardPath("QuickAwakened.png"), TEXT[10], new CaCaw(), new AwakenDeath(), new DarkVoid()));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("8"), NAMES[11], expansionContentMod.makeCardPath("QuickAncients.png"), TEXT[11], new DonusPower(), new DecasProtection(), new PolyBeam()));
        ArrayList<OctoChoiceCard> realList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            realList.add(cardList.remove(AbstractDungeon.cardRandomRng.random(cardList.size() - 1)));
        }
        return realList;
    }

    public void doChoiceStuff(OctoChoiceCard card) {
        AbstractCard q;
        AbstractCard r;
        AbstractCard z;
        switch (card.cardID) {
            case "expansioncontent:0": {
                q = new ChargeUp();
                r = new GuardianWhirl();
                z = new DefensiveMode();
                break;
            }
            case "expansioncontent:1": {
                q = new Hexaburn();
                r = new SuperHexaguard();
                z = new Sear();
                break;
            }
            case "expansioncontent:2": {
                q = new PrepareCrush();
                r = new GoopSpray();
                z = new SlimeTackle();
                break;
            }
            case "expansioncontent:3": {
                q = new BronzeBeam();
                r = new HyperBeam();
                z = new Flail();
                break;
            }
            case "expansioncontent:4": {
                q = new DefensiveStance();
                r = new FaceSlap();
                z = new LastStand();
                break;
            }
            case "expansioncontent:5": {
                q = new Torchfire();
                r = new Collect();
                z = new YouAreMine();
                break;
            }
            case "expansioncontent:6": {
                q = new TimeRipple();
                r = new Chronoboost();
                z = new ManipulateTime();
                break;
            }
            case "expansioncontent:7": {
                q = new DarkVoid();
                r = new CaCaw();
                z = new AwakenDeath();
                break;
            }
            case "expansioncontent:8": {
                q = new DonusPower();
                r = new DecasProtection();
                z = new PolyBeam();
                break;
            }
            default:{
                q = new DonusPower();
                r = new DecasProtection();
                z = new PolyBeam();
                break;
            }

        }
//        q.freeToPlayOnce = true;
//        r.freeToPlayOnce = true;
//        z.freeToPlayOnce = true;
        q.updateCost(-1);
        r.updateCost(-1);
        z.updateCost(-1);
        atb(new MakeTempCardInHandAction(q));
        atb(new MakeTempCardInHandAction(r));
        atb(new MakeTempCardInHandAction(z));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}