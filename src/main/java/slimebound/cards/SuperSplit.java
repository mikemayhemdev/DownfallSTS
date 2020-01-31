package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.PoisonSlime;
import slimebound.orbs.ShieldSlime;
import slimebound.orbs.SlimingSlime;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;


public class SuperSplit extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SuperSplit";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/supersplit.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = -1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;
    public static boolean UpgradeCard;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SuperSplit() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));
        ArrayList<Integer> orbs = new ArrayList();
        orbs.add(1);
        orbs.add(2);
        orbs.add(3);
        orbs.add(4);

        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        if (upgraded) this.energyOnUse++;
        if (p.hasRelic(ChemicalX.ID)) {
            this.energyOnUse += 2;
            p.getRelic(ChemicalX.ID).flash();
        }

        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction(this.energyOnUse));


        for (int i = 0; i < this.energyOnUse; i++) {
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
            }
        }

        p.energy.use(EnergyPanel.totalCount);


    }

    public AbstractCard makeCopy() {
        return new SuperSplit();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }
}

