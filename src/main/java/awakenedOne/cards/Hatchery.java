//package awakenedOne.cards;
//
//import awakenedOne.AwakenedOneMod;
//import awakenedOne.util.OnConjureSubscriber;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
//import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
//
//public class Hatchery extends AbstractAwakenedCard implements OnConjureSubscriber {
//    public final static String ID = AwakenedOneMod.makeID(Hatchery.class.getSimpleName());
//    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
//
//    public Hatchery() {
//        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
//        baseDamage = 4;
//        this.tags.add(AwakenedOneMod.DELVE);
//        loadJokeCardImage(this, makeBetaCardPath(Hatchery.class.getSimpleName() + ".png"));
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        CardCrawlGame.sound.playA("VO_CULTIST_1A", .3f);
//        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
//    }
//
//    @Override
//    public void OnConjure() {
//        this.addToBot(new DiscardToHandAction(this));
//    }
//
//    @Override
//    public void upp() {
//        upgradeDamage(2);
//    }
//}