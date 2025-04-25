package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.patches.AbstractCardEnum;


public class Split extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Split";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/split.png");
    private static final CardStrings cardStrings;

    public Split() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.BASIC, CardTarget.NONE);
        SlimeboundMod.loadJokeCardImage(this, "Split.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO - choose a slime, grant energy, command


        //SlimeboundMod.spawnNormalSlime();
        /*
        AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.PoisonSlime(), false, true, 0, 0));
        addToBot(new CommandAction());
        checkMinionMaster();

         */


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

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

