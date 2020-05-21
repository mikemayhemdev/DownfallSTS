package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.OctoChoiceCard;
import expansioncontent.actions.OctoChoiceAction;
import expansioncontent.expansionContentMod;
import guardian.patches.GuardianEnum;
import slimebound.actions.MakeTempCardInHandActionReduceCost;
import slimebound.patches.SlimeboundEnum;
import theHexaghost.TheHexaghost;

import java.util.ArrayList;

public class QuickStudy extends AbstractExpansionCard {

    public final static String ID = makeID("QuickStudy");

    //stupid intellij stuff SKILL, SELF, RARE

    public QuickStudy() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        if (AbstractDungeon.player.chosenClass != GuardianEnum.GUARDIAN)
            cardList.add(new OctoChoiceCard(expansionContentMod.makeID("0"), "Guardian", expansionContentMod.makeCardPath("QuickGuardian.png"), "Obtain Guardian expansioncontent:Boss cards."));
        if (AbstractDungeon.player.chosenClass != TheHexaghost.Enums.THE_SPIRIT)
            cardList.add(new OctoChoiceCard(expansionContentMod.makeID("1"), "Hexaghost", expansionContentMod.makeCardPath("QuickHexa.png"), "Obtain Hexaghost expansioncontent:Boss cards."));
        if (AbstractDungeon.player.chosenClass != SlimeboundEnum.SLIMEBOUND)
            cardList.add(new OctoChoiceCard(expansionContentMod.makeID("2"), "Slime", expansionContentMod.makeCardPath("QuickSlime.png"), "Obtain Slime Boss expansioncontent:Boss cards."));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("3"), "Automaton", expansionContentMod.makeCardPath("QuickAutomaton.png"), "Obtain Automaton expansioncontent:Boss cards."));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("4"), "Champ", expansionContentMod.makeCardPath("QuickChamp.png"), "Obtain Champ expansioncontent:Boss cards."));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("5"), "Collector", expansionContentMod.makeCardPath("QuickCollector.png"), "Obtain Collector expansioncontent:Boss cards."));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("6"), "TimeEater", expansionContentMod.makeCardPath("QuickTimeEater.png"), "Obtain Time Eater expansioncontent:Boss cards."));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("7"), "Awakened", expansionContentMod.makeCardPath("QuickAwakened.png"), "Obtain Awakened One expansioncontent:Boss cards."));
        cardList.add(new OctoChoiceCard(expansionContentMod.makeID("8"), "Ancients", expansionContentMod.makeCardPath("QuickAncients.png"), "Obtain Ancients expansioncontent:Boss cards."));
        ArrayList<OctoChoiceCard> realList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            realList.add(cardList.remove(AbstractDungeon.cardRandomRng.random(cardList.size() - 1)));
        }
        return realList;
    }

    public void doChoiceStuff(OctoChoiceCard card) {
        switch (card.cardID) {
            case "expansioncontent:0": {
                AbstractCard q = new ChargeUp();
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(q));
                AbstractCard r = new GuardianWhirl();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new DefensiveMode();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:1": {
                AbstractCard q = new Hexaburn();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new GhostWheel();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new Sear();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:2": {
                AbstractCard q = new PrepareCrush();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new GoopSpray();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new SlimeTackle();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:3": {
                AbstractCard q = new BronzeBeam();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new HyperBeam();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new Flail();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:4": {
                AbstractCard q = new DefensiveStance();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new FaceSlap();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new LastStand();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:5": {
                AbstractCard q = new Torchfire();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new Collect();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new YouAreMine();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:6": {
                AbstractCard q = new TimeRipple();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new Chronoboost();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new ManipulateTime();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:7": {
                AbstractCard q = new DarkVoid();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new CaCaw();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new AwakenDeath();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
            case "expansioncontent:8": {
                AbstractCard q = new DonusPower();
                q.cost--;
                if (upgraded) q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                AbstractCard r = new DecasProtection();
                if (upgraded) r.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(r));
                AbstractCard z = new PolyBeam();
                if (upgraded) z.upgrade();
                atb(new MakeTempCardInHandActionReduceCost(z));
                break;
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}